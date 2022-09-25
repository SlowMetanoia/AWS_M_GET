package Database.DataModel.Domain.CQCElement

import Database.DataModel.DomainModel
import Database.Signature.Model.HierarchyPartSignature

import java.util.UUID

case class CQCPartModel(id: UUID,
                        parentId: UUID,
                        elemType: String,
                        value: String) extends HierarchyPartSignature with DomainModel
