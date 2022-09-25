package Database.DataModel.Model

import Database.DataModel.DomainModel
import Database.Signature.Model.CQCDictionaryModelSignature

case class CQCDictionary(name: String) extends CQCDictionaryModelSignature with DomainModel
