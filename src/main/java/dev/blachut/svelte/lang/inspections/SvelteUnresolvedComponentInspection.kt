package dev.blachut.svelte.lang.inspections

import com.intellij.codeInspection.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.XmlElementVisitor
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiEditorUtil
import com.intellij.psi.xml.XmlTag
import dev.blachut.svelte.lang.codeInsight.SvelteComponentImporter
import dev.blachut.svelte.lang.isSvelteComponentTag

class SvelteUnresolvedComponentInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return object : XmlElementVisitor() {
            override fun visitXmlTag(tag: XmlTag) {
                if (!tag.isValid) return

                val componentName = tag.name
                if (!isSvelteComponentTag(componentName)) return
                if (tag.descriptor?.declaration != null) return

                val range = TextRange(1, tag.name.length + 1)

                val project = tag.project
                val fileName = "$componentName.svelte"
                // check if we have a corresponding svelte file
                val files = FilenameIndex.getVirtualFilesByName(project, fileName, GlobalSearchScope.allScope(project))
                if (files.isEmpty()) {
                    holder.registerProblem(tag, displayName, ProblemHighlightType.ERROR, range)
                    return
                }

                val file = tag.containingFile

                files.forEach { virtualFile ->
                    val modulesInfos = SvelteComponentImporter.getModulesInfos(project, file, virtualFile, componentName)
                    modulesInfos.forEach { info ->
                        val quickFix = object : LocalQuickFix {
                            override fun getFamilyName(): String {
                                return SvelteComponentImporter.getImportText(file, virtualFile, componentName, info)
                            }

                            override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
                                val editor = PsiEditorUtil.Service.getInstance().findEditorByPsiElement(tag) ?: return
                                SvelteComponentImporter.insertComponentImport(editor, file, virtualFile, componentName, info)
                            }
                        }
                        holder.registerProblem(tag, displayName, ProblemHighlightType.ERROR, range, quickFix)
                    }
                }
            }
        }
    }
}
