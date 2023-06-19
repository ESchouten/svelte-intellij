// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package dev.blachut.svelte.lang.service

import com.intellij.lang.javascript.ecmascript6.TypeScriptAnnotatorCheckerProvider
import com.intellij.lang.typescript.compiler.TypeScriptLanguageServiceAnnotatorCheckerProvider
import com.intellij.lang.typescript.compiler.languageService.protocol.commands.response.TypeScriptQuickInfoResponse
import com.intellij.lang.typescript.lsp.JSFrameworkLspTypeScriptService
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.openapi.util.text.StringUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerDescriptor
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.util.convertMarkupContentToHtml
import com.intellij.psi.PsiFile
import org.eclipse.lsp4j.MarkupContent

class SvelteLspTypeScriptService(project: Project) : JSFrameworkLspTypeScriptService(project) {
  override fun getProviderClass(): Class<out LspServerSupportProvider> = SvelteLspServerSupportProvider::class.java

  override val name = "Svelte LSP"
  override val prefix = "Svelte"
  override val serverVersion = svelteLanguageToolsVersion

  override fun createQuickInfoResponse(markupContent: MarkupContent): TypeScriptQuickInfoResponse {
    return TypeScriptQuickInfoResponse().apply {
      val content = HtmlBuilder().appendRaw(convertMarkupContentToHtml(markupContent)).toString()
      val parts = content.split("<hr />")

      displayString = parts[0]
        .removeSurrounding("<pre><code class=\"language-typescript\">", "</code></pre>")
        .trim()
        .let(StringUtil::unescapeXmlEntities)
      if (parts.size == 2) {
        documentation = parts[1]
      }
      // Svelte LS omits "export" so we can't assign kindModifiers
    }
  }

  override fun canHighlight(file: PsiFile): Boolean {
    val provider = TypeScriptAnnotatorCheckerProvider.getCheckerProvider(file)
    if (provider !is TypeScriptLanguageServiceAnnotatorCheckerProvider) return false

    return isFileAcceptableForService(file.virtualFile ?: return false)
  }

  override fun isAcceptable(file: VirtualFile) = isServiceEnabledAndAvailable(project, file)

  override fun isServiceEnabledBySettings(project: Project): Boolean {
    return isSvelteServiceEnabledBySettings(project)
  }

  override fun getLspServerDescriptor(project: Project, file: VirtualFile): LspServerDescriptor? {
    return getSvelteServerDescriptor(project, file)
  }
}
