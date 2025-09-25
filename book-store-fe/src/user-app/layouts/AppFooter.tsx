import { Link } from "react-router-dom";


const AppFooter: React.FC = () => {
  

  return (
    <footer className="bg-gray-900 text-white mt-12">
      <div className="max-w-7xl mx-auto px-6 py-12 grid grid-cols-1 md:grid-cols-3 gap-10">
        
        {/* About Section */}
        <div>
          <h2 className="text-xl font-semibold mb-4">📚 BlackCode Bookstore</h2>
          <p className="text-gray-400">
            Menyediakan ribuan buku dari berbagai genre, membantu kamu belajar, tumbuh, dan terinspirasi.
          </p>
        </div>

        {/* Navigation Links */}
        <div>
          <h2 className="text-xl font-semibold mb-4">Navigasi</h2>
          <ul className="space-y-2">
            <li><Link to="/" className="text-gray-400 hover:text-white">Beranda</Link></li>
            <li><Link to="/store" className="text-gray-400 hover:text-white">Toko Buku</Link></li>
            <li><Link to="/about" className="text-gray-400 hover:text-white">Tentang Kami</Link></li>
            <li><Link to="/contact" className="text-gray-400 hover:text-white">Kontak</Link></li>
          </ul>
        </div>

        {/* Contact & Social */}
        <div>
          <h2 className="text-xl font-semibold mb-4">Hubungi Kami</h2>
          <p className="text-gray-400">Email: support@blackcodebooks.com</p>
          <p className="text-gray-400">WhatsApp: +62 812 3456 7890</p>
          <div className="flex gap-4 mt-4">
            {/* Placeholder for social icons */}
            <a href="#" className="text-gray-400 hover:text-white">🌐</a>
            <a href="#" className="text-gray-400 hover:text-white">🐦</a>
            <a href="#" className="text-gray-400 hover:text-white">📘</a>
          </div>
        </div>
      </div>

      {/* Bottom Bar */}
      <div className="border-t border-gray-700 py-4 text-center text-gray-500 text-sm">
        © {new Date().getFullYear()} BlackCode Bookstore. All rights reserved.
      </div>
    </footer>
  );

};

export default AppFooter;
