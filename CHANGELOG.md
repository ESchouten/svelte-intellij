# Changelog

<h3>0.12.1</h3>
<ul>
    <li>Extend supported IDE version range to 2020.x</li>
    <li>Support basic interpolations inside style attributes</li>
</ul>

<h3>0.12.0</h3>
<ul>
    <li>Support for different CSS dialects via lang attribute</li>
    <li>Initial support for $-prefixed store references</li>
    <li>Enable completion of JS declarations from script tags</li>
    <li>Initial support for module context scripts (inside Svelte files only)</li>
    <li>More robust logic of resolving component declarations</li>
    <li>Unresolved components are highlighted the same as other identifiers</li>
    <li>Remove buggy prop insertion while completing tag name</li>
    <li>Stop inserting mustaches after typing = for attributes</li>
    <li>Enable quote matching</li>
    <li>Bug fixes & stability improvements</li>
</ul>

<h3>0.11.1</h3>
<ul>
    <li>Fix regression about not working import suggestions</li>
</ul>

<h3>0.11.0</h3>
<ul>
    <li>Stop annotating directives as unknown attributes</li>
    <li>Enable CSS references and completions</li>
    <li>Recognize $$props variable</li>
    <li>Highlight unresolved references inside script tag the same as inside template expressions</li>
    <li>Limit IDE finding references to variables defined in e.g. config files</li>
    <li>Fix IDE error occurring for empty shorthand attribute</li>
    <li>Minor fixes & stability improvements</li>
</ul>

<h3>0.10.0</h3>
<ul>
    <li>Support attribute value expressions</li>
    <li>Support shorthand attribute expressions</li>
    <li>Support spread attributes</li>
</ul>

<h3>0.9.1</h3>
<ul>
    <li>Fix buggy auto-inserted each block closing tag</li>
    <li>Correctly set minimum version compatibility to 2019.2</li>
</ul>

<h3>0.9.0</h3>
<ul>
    <li>Connect template expressions to definitions inside script tag</li>
    <li>Support complex JS expressions in Svelte tags</li>
    <li>Recognize more component imports</li>
    <li>Properly parse Svelte components with lowercase name matching HTML single tags</li>  
    <li>Add Svelte Component to New file menu</li>
    <li>Improve stability</li>
</ul>

<h3>0.8.0</h3>
<ul>
    <li>Add auto import for components!</li>
    <li>Highlight not imported components</li>
    <li>Mark unused component imports properly</li>
    <li>Fix Svelte blocks breaking on identifiers containing the words if, as or then</li>
    <li>Support @html & @debug expressions</li>
    <li>Display better error messages for incomplete code</li>
    <li>Extend supported IDE version range to 2019.2</li>
</ul>

<h3>0.7.0</h3>
<ul>
    <li>Support code formatting!</li>
    <li>Emmet-style expansions for Svelte tags! Try typing if[TAB]</li>
    <li>Auto indent when writing newline between Svelte tags</li>
    <li>Automatically insert closing Svelte tags</li>
    <li>Support folding regions (+/- icons in the gutter) for Svelte tags</li>
    <li>Support Comment with Line/Block Comment actions</li>
    <li>Highlight Svelte tag mustaches in the same color as keywords</li>
    <li>Improve parser behavior for incomplete code</li>
</ul>

<h3>0.6.0</h3>
<ul>
    <li>Add syntax highlighting for JS inside blocks & expressions (bar attributes)</li>
    <li>Add syntax highlighting for Svelte keywords</li>
    <li>Improve parser recovery after errors</li>
    <li>Additional minor improvements</li>
</ul>

<h3>0.5.0</h3>
<ul>
    <li>First public release</li>
</ul>
