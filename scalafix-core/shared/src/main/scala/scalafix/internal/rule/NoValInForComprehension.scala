package scalafix.internal.rule

import scala.meta._
import scala.meta.contrib._
import scalafix.v0._

case object NoValInForComprehension extends Rule("NoValInForComprehension") {

  override def description: String =
    "Rewrite that removes redundant val inside for-comprehensions"

  override def fix(ctx: RuleCtx): Patch = {
    ctx.tree.collect {
      case v: Enumerator.Val =>
        val valTokens =
          v.tokens.takeWhile(t => t.syntax == "val" || t.is[Whitespace])
        valTokens.map(ctx.removeToken).asPatch.atomic
    }.asPatch
  }

}
