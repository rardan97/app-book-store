export interface DataProductTransaksi {
  transaksiId: number;
  transaksiKode: string;
  transaksiTotal: string; // dalam bentuk string, misalnya "10000"
}

export interface DataProductPembelian {
  productId: number;
  productNama: string;
  productHarga: string;
  productQty: string;
  productTotalHarga: string;
}

export interface CheckoutPayload {
  dataProductTransaksi: DataProductTransaksi;
  dataProductPembelian: DataProductPembelian[];
}



