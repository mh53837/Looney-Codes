import React from 'react';


interface HomeProps {
    loggedInUser: string | null;
  }
  
  const Home: React.FC<HomeProps> = ({ loggedInUser }) => {
    return (
      <div>
        
        {loggedInUser ? (
          <div>
            <p>pozdrav, {loggedInUser}!</p>
            {/* Additional content for logged-in users */}
          </div>
        ) : (
          <div>
            <p>pozdrav, nepoznati korisnik!</p>
          </div>
        )}

        <p>*kalendar*</p>
      </div>
    );
  };

export default Home;