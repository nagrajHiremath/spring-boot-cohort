# Spring Security advance

## Production standard impl

### JWT access and refresh token
- refresh token = which is longer expiry only for access token genration without any claims
- when login -> return refresh token in cookie
- in refresh method -> return access token by verifying refresh token

### Oauth2 (Google, GitHub, Apple)

## Session management
one user can have K number of sessions.

- Session Entity (id, refreshToken, lastUsedAt, User-@ManyToOne)
- when Login - create session - if number of session limit reached - delete session and create new
- when refresh - check session exits with token if yes update last used and return access token, else throw

## Role based authorization

## Role and Authority

## @PreAuthorised and @Secured annotation
