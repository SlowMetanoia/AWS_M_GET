package Database.DataModel.Model

import Database.DataModel.DomainModel
import Database.Signature.Model.CQCHierarchyModelSignature

case class CQCHierarchy(childType: String,
                        parentType: String) extends CQCHierarchyModelSignature with DomainModel
