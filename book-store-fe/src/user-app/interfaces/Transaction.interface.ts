export interface CustomerDetails {
  first_name: string;
  last_name: string;
}

export interface ItemDetail {
  id: string;
  price: string;      // bisa ubah ke number jika yakin akan berupa angka
  quantity: string;   // bisa ubah ke number jika yakin akan berupa angka
  name: string;
}

export interface DataTotalTransaction {
  order_id: string;
  total_amount: string; // bisa ubah ke number jika akan dijumlahkan
}

export interface TransactionData {
  customerDetails: CustomerDetails;
  itemDetails: ItemDetail[];
  dataTotalTransaction: DataTotalTransaction;
  token: string;
}