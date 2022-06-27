import '../styles/globals.css'
import type { AppProps } from 'next/app'
import {ThemeProvider, createTheme } from "@mui/material";

const theme = createTheme();
function MyApp({ Component, pageProps }: AppProps) {
  return (
        <Component {...pageProps} />
  )
}

export default MyApp
