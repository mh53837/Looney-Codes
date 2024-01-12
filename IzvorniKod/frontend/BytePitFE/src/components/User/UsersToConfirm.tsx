let usersToConfirmValue:number = 0;

export const getUsersToConfirm = (): number => {
    return usersToConfirmValue;
  };
  
  export const setUsersToConfirm = (newValue: number): void => {
    usersToConfirmValue = newValue;
  };