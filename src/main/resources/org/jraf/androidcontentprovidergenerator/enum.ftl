<#if header??>
${header}
</#if>
package ${config.providerJavaPackage}.${entity.nameLowerCase};

/**
 * Possible values for the {@code ${field.nameLowerCase}} column of the {@code ${entity.nameLowerCase}} table.
 */
public enum ${field.enumName} {
    // @formatter:off
    <#list field.enumValues as enumValue>
    ${enumValue},
    </#list>
    // @formatter:on
}