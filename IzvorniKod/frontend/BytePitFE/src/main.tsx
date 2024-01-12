import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import { UserProvider } from './context/userContext.tsx';
import { ThemeProvider} from './context/themeContext.tsx';
const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

root.render(
    <React.StrictMode>
        <ThemeProvider>
            <UserProvider>
                <App />
            </UserProvider>
        </ThemeProvider>
    </React.StrictMode>
);
