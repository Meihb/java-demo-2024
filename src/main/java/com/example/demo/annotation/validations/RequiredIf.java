package com.example.demo.annotation.validations;

import com.example.demo.Validators.RequiredIfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * 在自定义注解 `@RequiredIf` 中，`@Target(ElementType.TYPE)` 是为了让注解应用于**类级别**的验证，而不是直接用于某个字段。这是因为 `@RequiredIf` 的验证逻辑涉及两个字段之间的关联：一个字段的值决定另一个字段是否为必填。要实现这种跨字段的验证，需要对整个对象（即类）进行校验，而不是单独的字段。
 *
 * ### 为什么 `@Target(ElementType.TYPE)`？
 *
 * 1. **跨字段验证**：
 *    `@RequiredIf` 是用来检查两个字段之间的逻辑关系，例如 "当字段 A 的值为某个特定值时，字段 B 必须有值"。这种跨字段的验证需要在整个对象上进行验证，无法仅仅作用于单一字段。
 *
 *    - **示例**：当 `status` 字段的值为 `"active"` 时，`email` 字段必须有值。这个逻辑需要了解整个对象的状态，而不仅仅是单独的字段。
 *
 * 2. **类级别的验证**：
 *    使用 `ElementType.TYPE` 表示注解是作用在类上的。这使得注解可以获取到对象中的所有字段，方便检查多个字段之间的关联关系。
 *
 * 3. **字段级别无法实现跨字段逻辑**：
 *    如果将 `@RequiredIf` 注解放在字段级别（例如 `ElementType.FIELD`），那么它只能获取到单个字段的信息，无法检查其他字段的状态。因此，要实现跨字段逻辑，必须将注解应用于类。
 *
 * ### 为什么不能用 `ElementType.FIELD`？
 *
 * `ElementType.FIELD` 适用于单字段的验证，如 `@NotNull`、`@Max` 等注解，它们只关心一个字段的值。如果你把 `@RequiredIf` 注解应用于字段级别，你只能检查该字段本身，而无法获取到其他字段的值。例如，如果你把 `@RequiredIf` 放在 `email` 字段上，你就无法知道 `status` 字段的值，因此也无法实现条件校验。
 *
 * ### 适合使用 `ElementType.FIELD` 的场景
 *
 * `ElementType.FIELD` 适用于单个字段的验证，比如：
 *
 * - `@NotNull`：字段不能为 null。
 * - `@Size`：字段的长度在指定范围内。
 * - `@Email`：字段必须是一个有效的电子邮件格式。
 *
 * 这些注解只关注字段本身的值，因此放在字段上是合适的。
 *
 * ### 示例
 *
 * 假设 `@RequiredIf` 被定义为作用在字段上：
 *
 * ```java
 * @RequiredIf(field = "status", requiredField = "email", fieldValue = "active")
 * private String email;
 * ```
 *
 * 这种情况下，注解只能对 `email` 字段进行校验，但它无法知道 `status` 的值。跨字段的逻辑验证无法实现。
 *
 * ### 总结
 *
 * - **`@Target(ElementType.TYPE)`** 使得 `@RequiredIf` 注解可以在类级别使用，从而在验证时可以访问类的多个字段。
 * - **跨字段验证** 必须在类级别上进行，因为它涉及多个字段之间的关联逻辑。
 */
@Documented
@Constraint(validatedBy = RequiredIfValidator.class)  // 指定验证逻辑类
@Target({ElementType.TYPE})  // 用于类级别的验证
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredIf {

    String message() default "This field is required.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // 要检查的控制字段（当该字段满足条件时，目标字段为必填）
    String field();

    // 需要验证的目标字段
    String requiredField();

    // 控制字段的期望值，当控制字段等于这个值时，目标字段为必填
    String fieldValue();
}

