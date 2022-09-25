package Database.DataModel.Domain.CQCElement

import Database.DataModel.DomainModel
import Database.Signature.Model.HierarchyLeafSignature

import java.util.UUID

case class CQCLeafModel(id: UUID,
                        parentId: UUID,
                        elemType: String,
                        value: String,
                       ) extends HierarchyLeafSignature with DomainModel
