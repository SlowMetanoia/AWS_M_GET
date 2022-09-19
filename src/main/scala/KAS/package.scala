import java.util.UUID

package object KAS {
  case class Knowledge(id:UUID,value:String) extends KAS
  case class Ability(id:UUID,value:String) extends KAS
  case class Skill(id:UUID,value:String) extends KAS

  case class KnowledgeKeyWord(id:UUID,value:String) extends KeyWord
  case class AbilityKeyWord(id:UUID,value:String) extends KeyWord
  case class SkillKeyWord(id:UUID,value:String) extends KeyWord
}
