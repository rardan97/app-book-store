import { useEffect, useRef, useState } from "react";
import "../../user.css";

interface Book {
  id: number;
  title: string;
  author: string;
  coverUrl: string;
}

const favoriteBooks: Book[] = [
  { id: 1, title: "The Great Gatsby", author: "F. Scott Fitzgerald", coverUrl: "https://source.unsplash.com/200x300/?book,classic" },
  { id: 2, title: "1984", author: "George Orwell", coverUrl: "https://source.unsplash.com/200x300/?book,dystopia" },
  { id: 3, title: "To Kill a Mockingbird", author: "Harper Lee", coverUrl: "https://source.unsplash.com/200x300/?book,novel" },
  { id: 4, title: "Pride and Prejudice", author: "Jane Austen", coverUrl: "https://source.unsplash.com/200x300/?book,romance" },
  { id: 5, title: "The Hobbit", author: "J.R.R. Tolkien", coverUrl: "https://source.unsplash.com/200x300/?book,fantasy" },
  { id: 6, title: "Moby Dick", author: "Herman Melville", coverUrl: "https://source.unsplash.com/200x300/?book,adventure" },
  { id: 7, title: "Moby Dick", author: "Herman Melville", coverUrl: "https://source.unsplash.com/200x300/?book,adventure" },
  { id: 8, title: "Moby Dick", author: "Herman Melville", coverUrl: "https://source.unsplash.com/200x300/?book,adventure" },
  { id: 9, title: "Moby Dick", author: "Herman Melville", coverUrl: "https://source.unsplash.com/200x300/?book,adventure" },
  { id: 10, title: "Moby Dick", author: "Herman Melville", coverUrl: "https://source.unsplash.com/200x300/?book,adventure" },
];

const BookFavorites: React.FC = () => {
  const scrollRef = useRef<HTMLDivElement>(null);
  const [canScrollLeft, setCanScrollLeft] = useState(false);
  const [canScrollRight, setCanScrollRight] = useState(true);

  const scrollAmount = 320;

  // Scroll handler with button disable logic
  const scroll = (direction: "left" | "right") => {
    if (!scrollRef.current) return;
    const newScrollLeft =
      direction === "left"
        ? Math.max(scrollRef.current.scrollLeft - scrollAmount, 0)
        : Math.min(scrollRef.current.scrollLeft + scrollAmount, scrollRef.current.scrollWidth - scrollRef.current.clientWidth);

    scrollRef.current.scrollTo({ left: newScrollLeft, behavior: "smooth" });
  };

  // Check scroll position to update buttons state
  const checkScrollPosition = () => {
    if (!scrollRef.current) return;
    const { scrollLeft, scrollWidth, clientWidth } = scrollRef.current;
    setCanScrollLeft(scrollLeft > 0);
    setCanScrollRight(scrollLeft < scrollWidth - clientWidth - 1); // -1 for floating point fix
  };

  useEffect(() => {
    checkScrollPosition();
    const current = scrollRef.current;
    if (!current) return;

    current.addEventListener("scroll", checkScrollPosition);
    window.addEventListener("resize", checkScrollPosition);

    return () => {
      if (!current) return;
      current.removeEventListener("scroll", checkScrollPosition);
      window.removeEventListener("resize", checkScrollPosition);
    };
  }, []);

  return (
    <>
    <div className="my-4">

    
    <section className="max-w-7xl mx-auto py-10 relative">
      <h2 className="text-3xl font-bold mb-8 text-gray-900">Favorite Books</h2>

      <div className="relative">
        {/* Left Arrow */}
        <button
          onClick={() => scroll("left")}
          aria-label="Scroll Left"
          disabled={!canScrollLeft}
          className={`absolute left-0 top-1/2 -translate-y-1/2 bg-white bg-opacity-80 hover:bg-opacity-100 text-gray-800 rounded-full p-3 shadow-lg z-20 transition disabled:opacity-40 disabled:cursor-not-allowed`}
          style={{ backdropFilter: "blur(6px)" }}
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={3}
          >
            <path strokeLinecap="round" strokeLinejoin="round" d="M15 19l-7-7 7-7" />
          </svg>
        </button>

        {/* Right Arrow */}
        <button
          onClick={() => scroll("right")}
          aria-label="Scroll Right"
          disabled={!canScrollRight}
          className={`absolute right-0 top-1/2 -translate-y-1/2 bg-white bg-opacity-80 hover:bg-opacity-100 text-gray-800 rounded-full p-3 shadow-lg z-20 transition disabled:opacity-40 disabled:cursor-not-allowed`}
          style={{ backdropFilter: "blur(6px)" }}
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={3}
          >
            <path strokeLinecap="round" strokeLinejoin="round" d="M9 5l7 7-7 7" />
          </svg>
        </button>

        {/* Scrollable cards */}
        <div
          ref={scrollRef}
          className="flex space-x-6 overflow-x-auto scroll-smooth scrollbar-none"
          style={{
            scrollbarWidth: "none", // Firefox
            msOverflowStyle: "none", // IE 10+
          }}
        >
          {favoriteBooks.map((book) => (
            <div
              key={book.id}
              className="min-w-[200px] my-10 flex-shrink-0 bg-white rounded-xl shadow-md hover:shadow-xl transform hover:scale-105 transition cursor-pointer"
            >
              <img
                src={book.coverUrl}
                alt={book.title}
                className="w-full h-56 object-cover rounded-t-xl"
                loading="lazy"
              />
              <div className="p-4">
                <h3 className="font-semibold text-lg text-gray-900 truncate">{book.title}</h3>
                <p className="text-gray-600 text-sm mt-1 truncate">{book.author}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
    </div>
     </>
  );
};

export default BookFavorites;