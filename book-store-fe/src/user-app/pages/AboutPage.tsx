const AboutPage: React.FC = () => {
  return (
    <div className="bg-gray-50 text-gray-800 py-12 px-6 md:px-16">
      <div className="max-w-5xl mx-auto">
        <h1 className="text-4xl font-bold text-center mb-8 text-indigo-700">
          Tentang Kami
        </h1>

        <section className="mb-12">
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">Siapa Kami?</h2>
          <p className="text-lg leading-relaxed text-gray-700">
            <span className="font-semibold text-indigo-600">BookNest</span> adalah toko buku online yang berdedikasi untuk 
            menyediakan akses mudah dan cepat terhadap buku-buku terbaik dari berbagai genre — mulai dari fiksi klasik, 
            non-fiksi modern, hingga literatur lokal. Kami percaya bahwa membaca bukan hanya kegiatan, tetapi gaya hidup.
          </p>
        </section>

        <section className="mb-12">
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">Visi Kami</h2>
          <p className="text-lg leading-relaxed text-gray-700">
            Menjadi platform toko buku digital terbaik di Indonesia yang mendukung pertumbuhan literasi, 
            memperkuat budaya membaca, dan menghubungkan pembaca dengan dunia lewat kata-kata.
          </p>
        </section>

        <section className="mb-12">
          <h2 className="text-2xl font-semibold mb-4 text-gray-800">Misi Kami</h2>
          <ul className="list-disc list-inside text-lg text-gray-700 space-y-2">
            <li>Menyediakan koleksi buku terkurasi dari penulis lokal dan internasional.</li>
            <li>Membantu pengguna menemukan buku yang sesuai dengan minat dan kebutuhan mereka.</li>
            <li>Mendukung penerbit dan penulis independen lewat kerja sama yang berkelanjutan.</li>
            <li>Menghadirkan pengalaman belanja buku yang cepat, mudah, dan menyenangkan.</li>
          </ul>
        </section>

        <section>
          <h2 className="text-2xl font-semibold mb-6 text-gray-800">Tim Kami</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {[
              { name: "Andi Pratama", role: "Founder & CEO" },
              { name: "Rina Kusuma", role: "Chief Content Officer" },
              { name: "Budi Santoso", role: "Tech Lead" },
            ].map((member, index) => (
              <div
                key={index}
                className="bg-white rounded-xl shadow hover:shadow-md p-4 text-center transition"
              >
                <div className="w-24 h-24 mx-auto mb-3 rounded-full bg-gray-200"></div>
                <h3 className="font-semibold text-lg">{member.name}</h3>
                <p className="text-sm text-gray-500">{member.role}</p>
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
};

export default AboutPage;