import {BadgesService} from './badges.service';

describe('BadgesServiceService', () => {

  let service;
  beforeEach(() => {
    service= new BadgesService();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should create badges from expert mode expression', () => {
    expect(service.generateBadges('c:axa global uwy:2015')).toEqual([{key: "Cedant Name", value: "axa global", operator: 'LIKE'}, {key: "year", value: "2015", operator: 'LIKE'}]);
    expect(service.generateBadges('axa')).toEqual('axa');
  })

});
