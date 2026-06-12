import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// Vite configuration for the React frontend application
// Configures the development server and React plugin
export default defineConfig({
  // plugins array contains the React plugin for JSX transformation
  plugins: [react()],
  // server configuration for the development environment
  server: { 
    // port 5173 is the default Vite development server port
    port: 5173 
  }
});
