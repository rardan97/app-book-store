
import ReactDOM from 'react-dom/client';
import AppUser from './AppUser';
import { StrictMode } from 'react';
import { BrowserRouter } from 'react-router-dom';


const root = ReactDOM.createRoot(document.getElementById('root')!);
root.render(
        <StrictMode>
    <BrowserRouter>
     <AppUser />
    </BrowserRouter>
       

        </StrictMode>
);