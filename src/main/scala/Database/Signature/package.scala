package Database

package object Signature {
  sealed trait Signature
  trait TableSignature extends Signature
  trait ModelSignature extends Signature
}
