create sequence aa_sequence_aa_sequence_ii_seq;
create sequence analysis_analysis_ii_seq;
create sequence analysis_data_analysis_data_ii_seq;
create sequence analysis_type_analysis_type_ii_seq;
create sequence attribute_attribute_ii_seq;
create sequence attribute_group_attribute_group_ii_seq;
create sequence attribute_nominal_value_attribute_nominal_value_ii_seq;
create sequence dataset_dataset_ii_seq;
create sequence drug_class_drug_class_ii_seq;
create sequence drug_commercial_drug_commercial_ii_seq;
create sequence drug_generic_drug_generic_ii_seq;
create sequence nt_sequence_nt_sequence_ii_seq;
create sequence patient_patient_ii_seq;
create sequence protein_protein_ii_seq;
create sequence query_definition_parameter_query_definition_parameter_ii_seq;
create sequence query_definition_parameter_type_query_definition_parameter_type_ii_seq;
create sequence query_definition_query_definition_ii_seq;
create sequence test_nominal_value_test_nominal_value_ii_seq;
create sequence test_object_test_object_ii_seq;
create sequence test_result_test_result_ii_seq;
create sequence test_test_ii_seq;
create sequence test_type_test_type_ii_seq;
create sequence therapy_therapy_ii_seq;
create sequence value_type_value_type_ii_seq;
create sequence viral_isolate_viral_isolate_ii_seq;
create table commercial_generic (generic_ii integer  not null, commercial_ii integer  not null, primary key (commercial_ii, generic_ii));
create table public.aa_insertion (position int2 not null, aa_sequence_ii integer  not null, insertion_order int2 not null, version integer  not null, aa_insertion varchar(30) not null, nt_insertion_codon varchar(255) not null, primary key (position, aa_sequence_ii, insertion_order));
create table public.aa_mutation (position int2 not null, aa_sequence_ii integer  not null, version integer  not null, aa_reference varchar(1) not null, aa_mutation varchar(30), nt_reference_codon varchar(255) not null, nt_mutation_codon varchar(255), primary key (position, aa_sequence_ii));
create table public.aa_sequence (aa_sequence_ii integer default nextval('aa_sequence_aa_sequence_ii_seq'), version integer  not null, nt_sequence_ii integer  not null, protein_ii integer  not null, first_aa_pos int2 not null, last_aa_pos int2 not null, primary key (aa_sequence_ii));
create table public.analysis (analysis_ii integer default nextval('analysis_analysis_ii_seq'), analysis_type_ii integer  not null, type integer , url varchar(100), account varchar(50), password varchar(100), baseinputfile varchar(50), baseoutputfile varchar(50), primary key (analysis_ii));
create table public.analysis_data (analysis_data_ii integer default nextval('analysis_data_analysis_data_ii_seq'), analysis_ii integer  not null, name varchar(50), data bytea, primary key (analysis_data_ii));
create table public.analysis_type (analysis_type_ii integer default nextval('analysis_type_analysis_type_ii_seq'), type varchar(50), primary key (analysis_type_ii));
create table public.attribute (attribute_ii integer default nextval('attribute_attribute_ii_seq'), version integer  not null, value_type_ii integer , attribute_group_ii integer , name varchar(50) not null, primary key (attribute_ii));
create table public.attribute_group (attribute_group_ii integer default nextval('attribute_group_attribute_group_ii_seq'), version integer  not null, group_name varchar(50), primary key (attribute_group_ii));
create table public.attribute_nominal_value (nominal_value_ii integer default nextval('attribute_nominal_value_attribute_nominal_value_ii_seq'), version integer  not null, attribute_ii integer  not null, value varchar(100) not null, primary key (nominal_value_ii));
create table public.dataset (dataset_ii integer default nextval('dataset_dataset_ii_seq'), version integer  not null, description varchar(50) not null, creation_date date not null, closed_date date, revision integer , primary key (dataset_ii));
create table public.dataset_access (uid varchar(50) not null, dataset_ii integer  not null, version integer  not null, permissions integer  not null, primary key (uid, dataset_ii));
create table public.drug_class (drug_class_ii integer default nextval('drug_class_drug_class_ii_seq'), version integer  not null, class_id varchar(10) not null, class_name varchar(100) not null, primary key (drug_class_ii));
create table public.drug_commercial (commercial_ii integer default nextval('drug_commercial_drug_commercial_ii_seq'), version integer  not null, name varchar(100) not null, primary key (commercial_ii));
create table public.drug_generic (generic_ii integer default nextval('drug_generic_drug_generic_ii_seq'), version integer  not null, drug_class_ii integer  not null, generic_id varchar(10) not null, generic_name varchar(50) not null, primary key (generic_ii));
create table public.nt_sequence (nt_sequence_ii integer default nextval('nt_sequence_nt_sequence_ii_seq'), version integer  not null, viral_isolate_ii integer  not null, nucleotides varchar(255), label varchar(50), sequence_date date, primary key (nt_sequence_ii));
create table public.patient (patient_ii integer default nextval('patient_patient_ii_seq'), version integer  not null, patient_id varchar(50) not null, last_name varchar(50), first_name varchar(50), birth_date date, death_date date, primary key (patient_ii));
create table public.patient_attribute_value (patient_ii integer  not null, attribute_ii integer  not null, version integer  not null, nominal_value_ii integer , value varchar(100), primary key (patient_ii, attribute_ii));
create table public.patient_dataset (dataset_ii integer  not null, patient_ii integer  not null, primary key (dataset_ii, patient_ii));
create table public.protein (protein_ii integer default nextval('protein_protein_ii_seq'), version integer  not null, abbreviation varchar(50) not null, full_name varchar(50), primary key (protein_ii));
create table public.query_definition (query_definition_ii integer default nextval('query_definition_query_definition_ii_seq'), uid varchar(50), name varchar(50), description varchar(255), query varchar(255), primary key (query_definition_ii));
create table public.query_definition_parameter (query_definition_parameter_ii integer default nextval('query_definition_parameter_query_definition_parameter_ii_seq'), query_definition_parameter_type_ii integer , query_definition_ii integer , name varchar(50), primary key (query_definition_parameter_ii));
create table public.query_definition_parameter_type (query_definition_parameter_type_ii integer default nextval('query_definition_parameter_type_query_definition_parameter_type_ii_seq'), name varchar(100) not null unique, primary key (query_definition_parameter_type_ii));
create table public.settings_user (uid varchar(50) not null, version integer  not null, test_ii integer , dataset_ii integer , chart_width integer  not null, chart_height integer  not null, password varchar(50), email varchar(100), first_name varchar(50), last_name varchar(50), admin bool, enabled bool, primary key (uid));
create table public.test (test_ii integer default nextval('test_test_ii_seq'), version integer  not null, analysis_ii integer  unique, test_type_ii integer  not null, description varchar(50) not null, service_class varchar(50), service_data varchar(255), service_config varchar(255), primary key (test_ii));
create table public.test_nominal_value (nominal_value_ii integer default nextval('test_nominal_value_test_nominal_value_ii_seq'), version integer  not null, test_type_ii integer  not null, value varchar(100) not null, primary key (nominal_value_ii));
create table public.test_object (test_object_ii integer default nextval('test_object_test_object_ii_seq'), version integer  not null, description varchar(50) not null, test_object_id integer , primary key (test_object_ii));
create table public.test_result (test_result_ii integer default nextval('test_result_test_result_ii_seq'), version integer  not null, test_ii integer  not null, generic_ii integer , viral_isolate_ii integer , nominal_value_ii integer , patient_ii integer  not null, nt_sequence_ii integer , value varchar(50), test_date date, sample_id varchar(50), primary key (test_result_ii));
create table public.test_type (test_type_ii integer default nextval('test_type_test_type_ii_seq'), version integer  not null, value_type_ii integer , test_object_ii integer  not null, description varchar(50) not null, primary key (test_type_ii));
create table public.therapy (therapy_ii integer default nextval('therapy_therapy_ii_seq'), version integer  not null, patient_ii integer  not null, start_date date not null, stop_date date, comment varchar(50), primary key (therapy_ii));
create table public.therapy_commercial (therapy_ii integer  not null, commercial_ii integer  not null, version integer  not null, day_dosage_units float8, primary key (therapy_ii, commercial_ii));
create table public.therapy_drugs (therapy_ii integer  not null, drugs varchar(255) not null, primary key (therapy_ii, drugs));
create table public.therapy_generic (therapy_ii integer  not null, generic_ii integer  not null, version integer  not null, day_dosage_mg float8, primary key (therapy_ii, generic_ii));
create table public.value_type (value_type_ii integer default nextval('value_type_value_type_ii_seq'), version integer  not null, description varchar(50) not null, min float8, max float8, multiple bool, primary key (value_type_ii));
create table public.viral_isolate (viral_isolate_ii integer default nextval('viral_isolate_viral_isolate_ii_seq'), version integer  not null, patient_ii integer  not null, sample_id varchar(50), sample_date date, primary key (viral_isolate_ii));
alter table commercial_generic add constraint "FK_commercial_generic_drug_generic" foreign key (generic_ii) references public.drug_generic(generic_ii) ON UPDATE CASCADE;
alter table commercial_generic add constraint "FK_commercial_generic_drug_commercial" foreign key (commercial_ii) references public.drug_commercial(commercial_ii) ON UPDATE CASCADE;
alter table public.aa_insertion add constraint "FK_aa_insertion_aa_sequence" foreign key (aa_sequence_ii) references public.aa_sequence(aa_sequence_ii) ON UPDATE CASCADE;
alter table public.aa_mutation add constraint "FK_aa_mutation_aa_sequence" foreign key (aa_sequence_ii) references public.aa_sequence(aa_sequence_ii) ON UPDATE CASCADE;
alter table public.aa_sequence add constraint "FK_aa_sequence_nt_sequence" foreign key (nt_sequence_ii) references public.nt_sequence(nt_sequence_ii) ON UPDATE CASCADE;
alter table public.aa_sequence add constraint "FK_aa_sequence_protein" foreign key (protein_ii) references public.protein(protein_ii) ON UPDATE CASCADE;
alter table public.analysis add constraint "FK_analysis_analysis_type" foreign key (analysis_type_ii) references public.analysis_type(analysis_type_ii) ON UPDATE CASCADE;
alter table public.analysis_data add constraint "FK_analysis_data_analysis" foreign key (analysis_ii) references public.analysis(analysis_ii) ON UPDATE CASCADE;
alter table public.attribute add constraint "FK_attribute_value_type" foreign key (value_type_ii) references public.value_type(value_type_ii) ON UPDATE CASCADE;
alter table public.attribute add constraint "FK_attribute_attribute_group" foreign key (attribute_group_ii) references public.attribute_group(attribute_group_ii) ON UPDATE CASCADE;
alter table public.attribute_nominal_value add constraint "FK_attribute_nominal_value_attribute" foreign key (attribute_ii) references public.attribute(attribute_ii) ON UPDATE CASCADE;
alter table public.dataset_access add constraint "FK_dataset_access_dataset" foreign key (dataset_ii) references public.dataset(dataset_ii) ON UPDATE CASCADE;
alter table public.dataset_access add constraint "FK_dataset_access_settings_user" foreign key (uid) references public.settings_user(uid) ON UPDATE CASCADE;
alter table public.drug_generic add constraint "FK_drug_generic_drug_class" foreign key (drug_class_ii) references public.drug_class(drug_class_ii) ON UPDATE CASCADE;
alter table public.nt_sequence add constraint "FK_nt_sequence_viral_isolate" foreign key (viral_isolate_ii) references public.viral_isolate(viral_isolate_ii) ON UPDATE CASCADE;
alter table public.patient_attribute_value add constraint "FK_patient_attribute_value_attribute" foreign key (attribute_ii) references public.attribute(attribute_ii) ON UPDATE CASCADE;
alter table public.patient_attribute_value add constraint "FK_patient_attribute_value_attribute_nominal_value" foreign key (nominal_value_ii) references public.attribute_nominal_value(nominal_value_ii) ON UPDATE CASCADE;
alter table public.patient_attribute_value add constraint "FK_patient_attribute_value_patient" foreign key (patient_ii) references public.patient(patient_ii) ON UPDATE CASCADE;
alter table public.patient_dataset add constraint "FK_patient_dataset_patient" foreign key (patient_ii) references public.patient(patient_ii) ON UPDATE CASCADE;
alter table public.patient_dataset add constraint "FK_patient_dataset_dataset" foreign key (dataset_ii) references public.dataset(dataset_ii) ON UPDATE CASCADE;
alter table public.query_definition add constraint "FK_query_definition_settings_user" foreign key (uid) references public.settings_user(uid) ON UPDATE CASCADE;
alter table public.query_definition_parameter add constraint "FK_query_definition_parameter_query_definition_parameter_type" foreign key (query_definition_parameter_type_ii) references public.query_definition_parameter_type(query_definition_parameter_type_ii) ON UPDATE CASCADE;
alter table public.query_definition_parameter add constraint "FK_query_definition_parameter_query_definition" foreign key (query_definition_ii) references public.query_definition(query_definition_ii) ON UPDATE CASCADE;
alter table public.settings_user add constraint "FK_settings_user_test" foreign key (test_ii) references public.test(test_ii) ON UPDATE CASCADE;
alter table public.settings_user add constraint "FK_settings_user_dataset" foreign key (dataset_ii) references public.dataset(dataset_ii) ON UPDATE CASCADE;
alter table public.test add constraint "FK_test_analysis" foreign key (analysis_ii) references public.analysis(analysis_ii) ON UPDATE CASCADE;
alter table public.test add constraint "FK_test_test_type" foreign key (test_type_ii) references public.test_type(test_type_ii) ON UPDATE CASCADE;
alter table public.test_nominal_value add constraint "FK_test_nominal_value_test_type" foreign key (test_type_ii) references public.test_type(test_type_ii) ON UPDATE CASCADE;
alter table public.test_result add constraint "FK_test_result_test" foreign key (test_ii) references public.test(test_ii) ON UPDATE CASCADE;
alter table public.test_result add constraint "FK_test_result_drug_generic" foreign key (generic_ii) references public.drug_generic(generic_ii) ON UPDATE CASCADE;
alter table public.test_result add constraint "FK_test_result_nt_sequence" foreign key (nt_sequence_ii) references public.nt_sequence(nt_sequence_ii) ON UPDATE CASCADE;
alter table public.test_result add constraint "FK_test_result_patient" foreign key (patient_ii) references public.patient(patient_ii) ON UPDATE CASCADE;
alter table public.test_result add constraint "FK_test_result_test_nominal_value" foreign key (nominal_value_ii) references public.test_nominal_value(nominal_value_ii) ON UPDATE CASCADE;
alter table public.test_result add constraint "FK_test_result_viral_isolate" foreign key (viral_isolate_ii) references public.viral_isolate(viral_isolate_ii) ON UPDATE CASCADE;
alter table public.test_type add constraint "FK_test_type_value_type" foreign key (value_type_ii) references public.value_type(value_type_ii) ON UPDATE CASCADE;
alter table public.test_type add constraint "FK_test_type_test_object" foreign key (test_object_ii) references public.test_object(test_object_ii) ON UPDATE CASCADE;
alter table public.therapy add constraint "FK_therapy_patient" foreign key (patient_ii) references public.patient(patient_ii) ON UPDATE CASCADE;
alter table public.therapy_commercial add constraint "FK_therapy_commercial_therapy" foreign key (therapy_ii) references public.therapy(therapy_ii) ON UPDATE CASCADE;
alter table public.therapy_commercial add constraint "FK_therapy_commercial_drug_commercial" foreign key (commercial_ii) references public.drug_commercial(commercial_ii) ON UPDATE CASCADE;
alter table public.therapy_generic add constraint "FK_therapy_generic_therapy" foreign key (therapy_ii) references public.therapy(therapy_ii) ON UPDATE CASCADE;
alter table public.therapy_generic add constraint "FK_therapy_generic_drug_generic" foreign key (generic_ii) references public.drug_generic(generic_ii) ON UPDATE CASCADE;
alter table public.viral_isolate add constraint "FK_viral_isolate_patient" foreign key (patient_ii) references public.patient(patient_ii) ON UPDATE CASCADE;
