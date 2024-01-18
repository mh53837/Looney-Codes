import React from 'react';

interface UserData {
    korisnickoIme: string;
    ime: string;
    prezime: string;
    email: string;
    uloga: string;
  }

interface UserProfileHeaderProps {
  imageData: string | null;
  userData: UserData;
}

const UserProfileHeader: React.FC<UserProfileHeaderProps> = ({ imageData, userData }) => {
  return (

      <div className="userProfile">
        <div className="userImg">
          {imageData ? (
            <img className="userProfileImg" src={imageData} alt="User icon" />
          ) : (
            <img className="userProfileImg" src="/slike/placeHolder.png" alt="Default icon" />
          )}
        </div>
        <div className="userData">
          <p>{userData.korisnickoIme}</p>
          <p>{userData.ime}&nbsp;{userData.prezime}</p>
          <p>{userData.email}</p>
        </div>
      </div>
  );
};

export default UserProfileHeader;