import { ThemeProvider } from "@/components/theme-provider";
// import { ModeToggle } from "@/components/mode-toggle";
import HomeHero from "@/components/HomeHero";
import NavBar from "@/components/NavBar";

export default function App() {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <div>
        <NavBar />
        <HomeHero />
      </div>
    </ThemeProvider>
  )
}