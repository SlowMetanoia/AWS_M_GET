package Database.DataModel.Domain

import Database.DataModel.DomainModel
import Database.Signature.Model.CQCHierarchyModelSignature

case class CQCHierarchyModel(childType: String,
                             parentType: String) extends CQCHierarchyModelSignature with DomainModel
