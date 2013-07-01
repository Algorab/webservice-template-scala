package de.caseclass.webservices.template.util

import org.slf4j.LoggerFactory

trait Logging {

  private val logger = LoggerFactory.getLogger(this.getClass)

  def debug(msg: => String) = if (logger.isDebugEnabled) logger.debug(msg)

  def info(msg: => String) = if (logger.isInfoEnabled) logger.info(msg)

  def error(msg: => String, cause: => Throwable) = if (logger.isErrorEnabled) logger.error(msg, cause)

  def error(msg: => String) = if (logger.isErrorEnabled) logger.error(msg)

  def warn(msg: => String) = if (logger.isWarnEnabled) logger.warn(msg)

  def trace(msg: => String) = if (logger.isTraceEnabled) logger.trace(msg)
}
