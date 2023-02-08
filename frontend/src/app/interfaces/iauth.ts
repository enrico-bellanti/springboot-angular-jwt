import { IRole } from "./irole";

export interface IAuth {
  id: string,
  email: string,
  accessToken: string,
  type: string,
  refreshToken: string,
  roles: IRole[]
}
