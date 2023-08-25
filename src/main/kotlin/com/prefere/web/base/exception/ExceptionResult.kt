package com.prefere.web.base.exception

import org.springframework.http.HttpStatus

class ExceptionResult(val statusCode: HttpStatus, val message: String)