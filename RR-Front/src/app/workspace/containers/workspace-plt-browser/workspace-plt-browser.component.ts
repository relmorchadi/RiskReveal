import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-workspace-plt-browser',
  templateUrl: './workspace-plt-browser.component.html',
  styleUrls: ['./workspace-plt-browser.component.scss']
})
export class WorkspacePltBrowserComponent implements OnInit {

  currentPlt = null;
  notChecked = false;
  checked = true;
  currencySelected: any;
  financialUnitSelected: any;

  plts : any = [
    {pltId: 1 ,systemTags:[{tagId: 1}, {tagId: 2}, {tagId: 3}], userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: false},
    {pltId: 2 ,systemTags:[], userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: false, checked: true},
    {pltId: 3 ,systemTags:[{tagId: 1}, {tagId: 5}, {tagId: 7}], userTags: [ {tagId: 3}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: false, checked: false},
    {pltId: 4 ,systemTags:[], userTags: [], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: true},
    {pltId: 5 ,systemTags:[{tagId: 1}, {tagId: 2}, {tagId: 5}], userTags: [{tagId: 1}, {tagId: 2}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: false, checked: false},
    {pltId: 6 ,systemTags:[{tagId: 1}, {tagId: 2}, {tagId: 3}], userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: false},
    {pltId: 7 ,systemTags:[{tagId: 5}, {tagId: 7}, {tagId: 3}], userTags: [{tagId: 1}, {tagId: 2}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: false},
    {pltId: 8 ,systemTags:[{tagId: 1}, {tagId: 2}, {tagId: 3}], userTags: [{tagId: 1}, {tagId: 2}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: true},
    {pltId: 9 ,systemTags:[{tagId: 1}, {tagId: 5}, {tagId: 3}], userTags: [{tagId: 2}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: false, checked: false},
    {pltId: 10 ,systemTags:[{tagId: 1}, {tagId: 2}, {tagId: 3}], userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: false},
    {pltId: 11 ,systemTags:[], userTags: [{tagId: 1}, {tagId: 2}, {tagId: 3}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: false},
    {pltId: 12 ,systemTags:[{tagId: 5}, {tagId: 2}, {tagId: 7}], userTags: [ {tagId: 2}, {tagId: 3}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: false, note: true, checked: true},
    {pltId: 13 ,systemTags:[{tagId: 1}, {tagId: 2}, {tagId: 3}], userTags: [{tagId: 1}, {tagId: 2}], pltName:"NATC-USM_RL_Imf.T1", peril:"TC" , regionPerilCode:"NATC-USM", regionPerilName: "North Atlantic", selected: false, grain:"liberty-NAHU", vendorSystem:"RMS RiskLink", rap:"North Atlantic", d: true, note: true, checked: false}
  ];T
  systemTags = [
    {tagId:'1', tagName:'TC', border: '#FFF', tagColor:'#7bbe31', innerTagContent:'1', innerTagColor:'#a2d16f'},
    {tagId:'2', tagName:'NATC-USM', border: '#FFF', tagColor:'#7bbe31', innerTagContent:'2', innerTagColor:'#a2d16f'},
    {tagId:'3', tagName:'Post-Inured', border: '#FFF', tagColor:'#006249', innerTagContent:'9', innerTagColor:'#4d917f'},
    {tagId:'4', tagName:'Pricing', border: '#FFF', tagColor:'#009575', innerTagContent:'0', innerTagColor:'#4db59e'},
    {tagId:'5', tagName:'Accumulation', border: '#FFF', tagColor:'#009575', innerTagContent:'2', innerTagColor:'#4db59e'},
    {tagId:'6', tagName:'Default', border: '#FFF', tagColor:'#06b8ff', innerTagContent:'1', innerTagColor:'#51cdff'},
    {tagId:'7', tagName:'Non-Default', border: '#FFF', tagColor:'#f5a623', innerTagContent:'0', innerTagColor:'#f8c065'},
  ];
  userTags = [
    {tagId:'1', tagName:'Pricing V1', border: '#FFF', tagColor:'#893eff', innerTagContent:'1', innerTagColor:'#ac78ff'},
    {tagId:'2', tagName:'Pricing V2', border:'#666666', tagColor:'#06b8ff', innerTagContent:'2', innerTagColor:'#51cdff'},
    {tagId:'3', tagName:'Final Princing', border: '#FFF', tagColor:'#c38fff', innerTagContent:'5', innerTagColor:'#d5b0ff'}
  ];

paths = [
  {id: '1', icon: 'icon-assignment_24px', text: 'P-1686 Ameriprise 2018'},
  {id: '2', icon: 'icon-assignment_24px', text: 'P-1724 Trinity Kemper 2018'},
  {id: '3', icon: 'icon-assignment_24px', text: 'P-8592 TWIA 2018'},
  {id: '4', icon: 'icon-assignment_24px', text: 'P-9035 Post-insured PLTs'},
  {id: '5', icon: 'icon-sitemap', text: 'IP-1135 CXL2 Cascading 2018'}
];

currencies = [
  {id: '1', name: 'Euro', label: 'EUR'},
  {id: '2', name: 'Us Dollar', label: 'USD'},
  {id: '3', name: 'Britsh Pound', label: 'GBP'},
  {id: '4', name: 'Canadian Dollar', label: 'CAD'},
  {id: '5', name: 'Moroccan Dirham', label: 'MAD'},
  {id: '5', name: 'Swiss Franc', label: 'CHF'},
  {id: '5', name: 'Saudi Riyal', label: 'SAR'},
  {id: '6', name: 'Bitcoin', label: 'XBT'}
];

units = [
  {id: '1', label: 'Hundred'},
  {id: '2', label: 'Thousand'},
  {id: '3', label: 'Million'},
  {id: '4', label: 'Billion'},
  {id: '5', label: 'Trillion'}
];


  constructor() { }


  ngOnInit() {
  }

  visible = false;
  size = 'large';

  openDrawer(): void {
    this.visible = true;
  }

  closeDrawer(): void {
    this.visible = false;
  }

  openPltInDrawer(plt) {
    this.plts.forEach(pt => pt.selected = false);
    plt.selected = true;
    this.visible = true;
  }

  getColor(id, type){
    let item;
    if (type === 'system'){
       item = this.systemTags.filter( tag => tag.tagId == id );
    } else {
      item = this.userTags.filter( tag => tag.tagId == id );
    }
    if (item.length > 0)
      return item[0].tagColor;
    return null
  }
}
