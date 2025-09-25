import React, { createContext, useContext, useState } from 'react';
import type { TransactionData } from '../interfaces/Transaction.interface';

interface TransactionContextType {
  transactionData: TransactionData | null;
  setTransactionData: (data: TransactionData) => void;
  clearTransactionData: () => void;
}

const TransactionContext = createContext<TransactionContextType | undefined>(undefined);

export const TransactionProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [transactionData, setTransaction] = useState<TransactionData | null>(null);

  const setTransactionData = (data: TransactionData) => {
    setTransaction(data);
  };

  const clearTransactionData = () => {
    setTransaction(null);
  };

  return (
    <TransactionContext.Provider value={{ transactionData, setTransactionData, clearTransactionData }}>
      {children}
    </TransactionContext.Provider>
  );
};

export const useTransaction = (): TransactionContextType => {
  const context = useContext(TransactionContext);
  if (!context) throw new Error('useTransaction must be used within a TransactionProvider');
  return context;
};