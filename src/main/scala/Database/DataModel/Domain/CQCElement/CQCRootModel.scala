package Database.DataModel.Domain.CQCElement

import Database.DataModel.DomainModel
import Database.Signature.Model.HierarchyRootSignature

import java.util.UUID

case class CQCRootModel(id: UUID,
                        elemType: String,
                        value: String) extends HierarchyRootSignature with DomainModel
