import { ThemeProvider } from "@/components/theme-provider";
import { ModeToggle } from "@/components/mode-toggle";
import TagLine from "@/components/TagLine";

export default function App() {
  return (
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <ModeToggle/>
      <div>
        <TagLine />
      </div>
    </ThemeProvider>
  )
}