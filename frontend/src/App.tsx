import { ThemeProvider } from "@/components/theme-provider";
import { ModeToggle } from "@/components/mode-toggle";
import HomeHero from "@/components/HomeHero";

export default function App() {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <ModeToggle/>
      <div>
        <HomeHero />
      </div>
    </ThemeProvider>
  )
}