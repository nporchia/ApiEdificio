/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {},
    screens: {
      'xs': '320px',
      // => @media (min-width: 320px) { ... }
      'sm': '640px',
      // => @media (min-width: 640px) { ... }

      'md': '768px',
      // => @media (min-width: 768px) { ... }

      'lg': '1024px',
      // => @media (min-width: 1024px) { ... }

      'xl': '1280px',
      // => @media (min-width: 1280px) { ... }

      '2xl': '1536px',
      // => @media (min-width: 1536px) { ... }
    },
  },
  styles: {
    // Define your custom styles here
    '.rounded-tr th:first-child': {
      borderTopLeftRadius: '0.5rem',
    },
    '.max-h-xl': {
      maxHeight: '26rem',
    },
    '.rounded-tr th:last-child': {
      borderTopRightRadius: '0.5rem',
    },
    '.rounded-tr:hover th:first-child': {
      borderTopLeftRadius: '0.5rem',
    },
    '.rounded-tr:hover th:last-child': {
      borderTopRightRadius: '0.5rem',
    },
    '.transition-bg': {
      transition: 'background-color 0.3s ease'
    },
    
    /* Cambia el fondo cuando el elemento se vuelve pegajoso */
    '.sticky thead.transition-bg': {
      backgroundColor: '#ff5733' /* Cambia el color a tu preferencia */
    }
  },
  darkMode: 'class',
  plugins: [],
}

