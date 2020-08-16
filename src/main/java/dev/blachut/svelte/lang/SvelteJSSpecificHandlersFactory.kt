package dev.blachut.svelte.lang

import com.intellij.lang.ecmascript6.ES6SpecificHandlersFactory
import com.intellij.lang.javascript.psi.impl.JSReferenceExpressionImpl
import com.intellij.psi.impl.source.resolve.ResolveCache
import dev.blachut.svelte.lang.codeInsight.SvelteJSReferenceExpressionResolver

class SvelteJSSpecificHandlersFactory : ES6SpecificHandlersFactory() {
    override fun createReferenceExpressionResolver(
        referenceExpression: JSReferenceExpressionImpl,
        ignorePerformanceLimits: Boolean
    ): ResolveCache.PolyVariantResolver<JSReferenceExpressionImpl> {
        return SvelteJSReferenceExpressionResolver(referenceExpression, ignorePerformanceLimits)
    }
}
