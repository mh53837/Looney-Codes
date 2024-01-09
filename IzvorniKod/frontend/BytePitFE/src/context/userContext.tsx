import { createContext, useState, useEffect, ReactNode, Dispatch, SetStateAction } from 'react';

interface User {
  korisnickoIme: string;
  lozinka: string;
  uloga: string;
}

interface UserContextValue {
  user: User;
  setUser: Dispatch<SetStateAction<User>>;
}

export const UserContext = createContext<UserContextValue>({
  user: {
    korisnickoIme: '',
    lozinka: '',
    uloga: '',
  },
  setUser: () => { },
});

interface UserProviderProps {
  children: ReactNode;
}

export const UserProvider = ({ children }: UserProviderProps) => {
  const [user, setUser] = useState<User>(() => {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : { korisnickoIme: '', lozinka: '', uloga: '' };
  });

  useEffect(() => {
    localStorage.setItem('user', JSON.stringify(user));
  }, [user]);

  return <UserContext.Provider value={{ user, setUser }}>{children}</UserContext.Provider>;
};