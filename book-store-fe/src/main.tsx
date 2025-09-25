import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter } from 'react-router-dom'
import App from './App.tsx'

import { VITE_MIDTRANS_CLIENT_KEY } from './config.ts'


// Inject Midtrans Snap script
const midtransScript = document.createElement('script')
midtransScript.src = 'https://app.sandbox.midtrans.com/snap/snap.js'
midtransScript.setAttribute('data-client-key', VITE_MIDTRANS_CLIENT_KEY || '')
document.head.appendChild(midtransScript)

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
     <App />
    </BrowserRouter>
       
  </StrictMode>,
)