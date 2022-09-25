package Database.DataModel.Domain

import Database.DataModel.DomainModel
import Database.Signature.Model.CQCElementDictionaryModelSignature

case class CQCDictionaryModel(name: String) extends CQCElementDictionaryModelSignature with DomainModel
