import { Link } from "react-router-dom";

const HeroSection: React.FC = () => {
return (
    <section className="bg-gradient-to-r from-indigo-700 via-purple-700 to-pink-600 text-white">
    <div className="max-w-7xl mx-auto px-6 py-16 flex flex-col-reverse md:flex-row items-center justify-between gap-10">
        
        {/* Text Section */}
        <div className="w-full md:w-1/2 text-center md:text-left">
        <h1 className="text-4xl md:text-5xl font-extrabold leading-tight mb-4">
            Discover Your Next Favorite <span className="text-yellow-300">Book</span>
        </h1>
        <p className="text-lg md:text-xl text-white/90 mb-6">
            Explore thousands of books from every genre and author. Whether you're learning or escaping, we’ve got the perfect story for you.
        </p>
        <div className="flex flex-col sm:flex-row gap-4 justify-center md:justify-start">
            <Link
            to="/store"
            className="bg-yellow-400 text-indigo-900 font-semibold px-6 py-3 rounded-md hover:bg-yellow-300 transition"
            >
            Browse Books
            </Link>
            <Link
            to="/about"
            className="border border-white px-6 py-3 rounded-md hover:bg-white hover:text-purple-800 transition"
            >
            Learn More
            </Link>
        </div>
        </div>

        {/* Image Section */}
        <div className="w-full md:w-1/2 flex justify-center">
        <img
            src="https://source.unsplash.com/500x400/?books,reading"
            alt="Books Illustration"
            className="w-full max-w-md rounded-lg shadow-lg"
        />
        </div>
    </div>
    </section>
);
};

export default HeroSection;