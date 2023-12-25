import { useEffect } from "react";
import AppRoutes from "./AppRoutes";
import { useTheme } from "./context/ThemeProvider";

function App() {
    const { theme } = useTheme();

    useEffect(() => {
        if(theme.mode === 'dark'){
          document.body.classList.add('dark')
        }else{
          document.body.classList.remove('dark')
        }
      }, [theme.mode])
    
    return (
        <div >
             <AppRoutes />
        </div>
      
    );
}

export default App;
