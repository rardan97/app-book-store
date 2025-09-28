import BookList from "../components/book/BookList";
import CategoryBook from "../components/book/CategoryBook";
import HeroSection from "../components/book/HeroSection";


export default function Home() {
    return (
        <>
        <div>
            <HeroSection />
            <CategoryBook />
            <BookList />
        </div>
        </>
    );
}
