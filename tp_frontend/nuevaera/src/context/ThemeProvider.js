import { createContext, useContext, useEffect, useState } from "react";

const ThemeContext = createContext({});

export const useTheme = () => useContext(ThemeContext);

export default function ThemeProvider({ children }) {
    const [theme, setTheme] = useState({
        mode: localStorage.getItem("theme") || "light",
    });

    useEffect(() => {
        localStorage.setItem("theme", theme.mode);
    }, [theme]);

    return (
        <ThemeContext.Provider value={{theme, setTheme}}>
            {children}
        </ThemeContext.Provider>
    );
}


