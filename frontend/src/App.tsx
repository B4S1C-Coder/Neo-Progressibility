import { ThemeProvider } from "@/components/theme-provider";

import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider
} from "react-router-dom";

import ExternalSiteLayout from "@/layouts/ExternalSiteLayout";

import HomePage from "@/pages/HomePage";
import AboutPage from "@/pages/AboutPage";
import SignInPage from "@/pages/SignInPage";
import SignUpPage from "@/pages/SignUpPage";
import DashboardPage from "./pages/DashboardPage";

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/" element={<ExternalSiteLayout />}>
        <Route index element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/about" element={<AboutPage />} />
      </Route>
      <Route path="/signin" element={<SignInPage />}/>
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/app" element={<DashboardPage />} />
    </>
  )
);

export default function App() {

  return (
    <div className="selection:bg-red-400 selection:text-white">
      <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
        <RouterProvider router={router} />
      </ThemeProvider>
    </div>
  )
}