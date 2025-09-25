import React from "react";

interface Book {
  id: number;
  title: string;
  author: string;
  price: string;
  coverUrl: string;
}

const bestSellers: Book[] = [
  {
    id: 1,
    title: "The Midnight Library",
    author: "Matt Haig",
    price: "$14.99",
    coverUrl: "https://source.unsplash.com/200x300/?book,library",
  },
  {
    id: 2,
    title: "Atomic Habits",
    author: "James Clear",
    price: "$19.99",
    coverUrl: "https://source.unsplash.com/200x300/?book,habits",
  },
  {
    id: 3,
    title: "Where the Crawdads Sing",
    author: "Delia Owens",
    price: "$16.99",
    coverUrl: "https://source.unsplash.com/200x300/?book,nature",
  },
  {
    id: 4,
    title: "Becoming",
    author: "Michelle Obama",
    price: "$22.99",
    coverUrl: "https://source.unsplash.com/200x300/?book,biography",
  },
];

const BookBestSellers: React.FC = () => {
  // Contoh fungsi handler tombol Add to Cart
  const handleAddToCart = (book: Book) => {
    alert(`Added "${book.title}" to cart!`);
  };

  // Contoh fungsi handler tombol Buy Now
  const handleBuyNow = (book: Book) => {
    alert(`Buying "${book.title}" now!`);
  };

  return (
    <section className="max-w-7xl mx-auto px-4 py-12 ">
      <h2 className="text-3xl font-bold text-gray-900 mb-8 text-center">Best Sellers</h2>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
        {bestSellers.map((book) => (
          <div
            key={book.id}
            className="bg-gray-50 rounded-lg shadow-md hover:shadow-xl transition transform hover:scale-105 flex flex-col"
          >
            <img
              src={book.coverUrl}
              alt={book.title}
              className="rounded-t-lg object-cover w-full h-64"
              loading="lazy"
            />
            <div className="p-5 flex flex-col flex-grow">
              <h3 className="text-lg font-semibold text-gray-900 mb-1">{book.title}</h3>
              <p className="text-sm text-gray-600 mb-4">by {book.author}</p>
              <div className="mt-auto px-4 py-2">
                {/* Baris price di mobile center, di desktop kiri */}
                <div className=" md:text-left mb-2 md:mb-0">
                  <span className="text-lg font-bold text-indigo-600 whitespace-nowrap">{book.price}</span>
                </div>

                {/* Baris tombol Buy dan Add: horizontal, full width di mobile, auto di desktop */}
                <div className="flex space-x-2 justify-center md:justify-start">
                  <button
                    onClick={() => handleBuyNow(book)}
                    className="bg-indigo-600 text-white px-4 py-1.5 rounded-md hover:bg-indigo-700 transition flex-1 md:flex-none text-sm"
                    aria-label={`Buy ${book.title}`}
                  >
                    Buy Now
                  </button>
                  <button
                    onClick={() => handleAddToCart(book)}
                    className="bg-green-600 text-white px-4 py-1.5 rounded-md hover:bg-green-700 transition flex-1 md:flex-none text-sm"
                    aria-label={`Add ${book.title} to Cart`}
                  >
                    Add Cart
                  </button>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default BookBestSellers;