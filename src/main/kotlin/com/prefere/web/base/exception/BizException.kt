package com.prefere.web.base.exception


class BizException(val baseResponseCode: BizResponseCode): RuntimeException() {
}