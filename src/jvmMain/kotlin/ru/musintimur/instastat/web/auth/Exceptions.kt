package ru.musintimur.instastat.web.auth

class InvalidCredentialsException(message: String = "Неверные логин или пароль."): IllegalArgumentException(message)
class InsufficientUsePrivilegesException(message: String = "Недостаточно привелегий."): IllegalStateException(message)
class UserNotFoundException(message: String = "Пользователь не найден."): IllegalArgumentException(message)
class ForbiddenRemoteAccess(message: String = "Доступ к этому ресурсу с удаленного зоста запрещен."): UnsupportedOperationException(message)