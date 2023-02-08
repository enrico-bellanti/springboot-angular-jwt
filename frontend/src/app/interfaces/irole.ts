import { IPermission } from './ipermission';

export interface IRole {
  id: string,
  name: string,
  permissions: IPermission[]
}
