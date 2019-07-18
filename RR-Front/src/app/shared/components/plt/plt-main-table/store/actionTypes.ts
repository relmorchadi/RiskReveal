import { Input } from './input';
import * as Types from './types';
import * as _ from 'lodash';

export const Actions = {
  updateKey: {
    type: Types.updateKey,
    handler: (rightMenuInput: Input, key, value): Input => ({
      ...rightMenuInput,
      [key]: value
    })
  }
}
