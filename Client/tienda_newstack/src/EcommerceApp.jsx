import { BrowserRouter } from 'react-router-dom'
import { AppRouter } from './router/AppRouter'

import './index.css'
import { AuthProvider } from './context/AuthContext'

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <AppRouter/>
      </BrowserRouter>
    </AuthProvider>
  )
}

export default App
