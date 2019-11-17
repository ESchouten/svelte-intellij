package dev.blachut.svelte.lang.codeInsight

import com.intellij.lang.javascript.psi.JSEmbeddedContent
import com.intellij.lang.javascript.psi.JSRecursiveWalkingElementVisitor
import com.intellij.lang.javascript.psi.JSVarStatement
import com.intellij.psi.PsiElement
import com.intellij.psi.XmlElementVisitor
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlDocument
import com.intellij.psi.xml.XmlFile
import com.intellij.psi.xml.XmlTag
import com.intellij.xml.util.HtmlUtil

internal class PropsVisitor : JSRecursiveWalkingElementVisitor() {
    val props = mutableListOf<String?>()

    override fun visitJSVarStatement(node: JSVarStatement?) {
        val text = node?.text ?: return
        if (text.trim().startsWith("export")) {
            val declarations = node.declarations
            props.addAll(declarations.map { it.name })
        }
    }
}

internal class SvelteScriptVisitor : SvelteHtmlFileVisitor() {
    var jsElement: JSEmbeddedContent? = null
    var scriptTag: XmlTag? = null

    override fun visitXmlTag(tag: XmlTag?) {
        if (HtmlUtil.isScriptTag(tag)) {
            scriptTag = tag
            jsElement = PsiTreeUtil.findChildOfType(tag, JSEmbeddedContent::class.java)
        }
    }
}

internal open class SvelteHtmlFileVisitor : XmlElementVisitor() {
    override fun visitXmlDocument(document: XmlDocument?): Unit = recursion(document)

    override fun visitXmlFile(file: XmlFile?): Unit = recursion(file)

    private fun recursion(element: PsiElement?) {
        element?.children?.forEach { it.accept(this) }
    }
}
