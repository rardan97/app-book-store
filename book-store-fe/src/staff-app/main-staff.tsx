
import ReactDOM from 'react-dom/client';
import AppStaff from './AppStaff';
import { ThemeProvider } from './context/ThemeContext';
import { AppWrapper } from './components/common/PageMeta';


const root = ReactDOM.createRoot(document.getElementById('root')!);
root.render(

    
    
<ThemeProvider>
    <AppWrapper>
        <AppStaff />
    </AppWrapper>
</ThemeProvider>

);
