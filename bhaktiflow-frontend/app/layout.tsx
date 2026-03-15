import type { Metadata } from "next";
import { Playfair_Display, DM_Sans } from "next/font/google";
import "./globals.css";
import Providers from "./Providers";
import Header from "@/components/Header";
import MusicPlayer from "@/components/MusicPlayer";
import Sidebar from "@/components/Sidebar";

const playfair = Playfair_Display({
  subsets: ["latin"],
  weight: ["700", "900"],
  style: ["normal", "italic"],
  variable: "--font-playfair",
});

const dmSans = DM_Sans({
  subsets: ["latin"],
  weight: ["300", "400", "500", "600", "700"],
  variable: "--font-dm-sans",
});

export const metadata: Metadata = {
  title: "BhaktiFlow | Sacred Frequencies",
  description: "Your daily spiritual companion",
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en" className={`${playfair.variable} ${dmSans.variable}`}>
      <body className="antialiased" style={{ fontFamily: "var(--font-dm-sans), sans-serif" }}>
        <Providers>
          {/* Watercolor gradient background */}
          <div
            className="fixed inset-0 -z-10"
            style={{
              background: `
                radial-gradient(circle at 15% 20%, rgba(59,130,246,0.30), transparent 45%),
                radial-gradient(circle at 85% 25%, rgba(34,211,238,0.35), transparent 45%),
                radial-gradient(circle at 20% 80%, rgba(236,72,153,0.28), transparent 50%),
                radial-gradient(circle at 80% 85%, rgba(251,146,60,0.40), transparent 50%),
                linear-gradient(135deg, #e0f2fe, #fdf4ff, #fff7ed)
              `,
            }}
          />
          <div className="fixed bottom-[-250px] right-[-250px] w-[600px] h-[600px] bg-orange-300/30 blur-[160px] rounded-full -z-10 pointer-events-none" />

          <Sidebar />
          <Header />

          <main className="min-h-screen pt-28 pb-36">
            {children}
          </main>

          <MusicPlayer />
        </Providers>
      </body>
    </html>
  );
}
