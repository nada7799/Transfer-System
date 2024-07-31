package com.TransferApp.MoneyTransfer.utils;

import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@PreAuthorize("@userAuthorizationService.canAccessUser(#id, authentication.name)")
public @interface HasUserAccess {
}
